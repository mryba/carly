package org.carly.core.paymentcardmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.carly.api.rest.request.PaymentCardRequest;
import org.carly.api.rest.response.PaymentCardResponse;
import org.carly.api.rest.response.SuccessResponse;
import org.carly.core.paymentcardmanagement.mapper.PaymentCardRequestMapper;
import org.carly.core.paymentcardmanagement.mapper.PaymentCardResponseMapper;
import org.carly.core.paymentcardmanagement.model.PaymentCard;
import org.carly.core.paymentcardmanagement.repository.PaymentCardRepository;
import org.carly.core.security.service.LoggedUserProvider;
import org.carly.core.shared.exception.EntityNotFoundException;
import org.carly.core.shared.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.carly.core.shared.utils.InfoUtils.NOT_FOUND;

@Service
@Slf4j
public class PaymentCardSaveService {

    private static final Long ZERO = 0L;

    private final PaymentCardRequestMapper paymentCardRequestMapper;
    private final PaymentCardResponseMapper paymentCardResponseMapper;
    private final PaymentCardRepository paymentCardRepository;
    private final LoggedUserProvider loggedUserProvider;

    public PaymentCardSaveService(PaymentCardRequestMapper paymentCardRequestMapper,
                                  PaymentCardResponseMapper paymentCardResponseMapper,
                                  PaymentCardRepository paymentCardRepository,
                                  LoggedUserProvider loggedUserProvider) {
        this.paymentCardRequestMapper = paymentCardRequestMapper;
        this.paymentCardResponseMapper = paymentCardResponseMapper;
        this.paymentCardRepository = paymentCardRepository;
        this.loggedUserProvider = loggedUserProvider;
    }

    public ResponseEntity<PaymentCardResponse> createPaymentCard(PaymentCardRequest request) {
        String userId = loggedUserProvider.loggedUser().getId().toHexString();
        PaymentCard paymentCard = paymentCardRequestMapper.simplifyDomainObject(request);
        paymentCard.setUserId(loggedUserProvider.loggedUser().getId().toHexString());
        paymentCard.setCreatedDate(LocalDateTime.now());
        paymentCard.setModifiedDate(LocalDateTime.now());
        // todo:
        if (request.isCardActive()) {
            deactivatePaymentCard();
        }
        // Always set first payment card as active one
        Long paymentCardCount = paymentCardRepository.countByUserId(userId)
                .orElse(ZERO);
        log.info("Found {} payment card for user with id: {}", paymentCardCount, userId);
        if (paymentCardCount.equals(ZERO)) {
            paymentCard.setCardActive(true);
        }
        paymentCardRepository.save(paymentCard);
        PaymentCardResponse response = paymentCardResponseMapper.simplifyRestObject(paymentCard);
        log.info("Payment card successfully created!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<PaymentCardResponse> updatePaymentCard(PaymentCardRequest request) {
        PaymentCard paymentCardToUpdate = paymentCardRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        if (request.isCardActive()) {
            deactivatePaymentCard();
        }
        PaymentCard updatedPaymentCard = paymentCardRequestMapper.mapToDomainObject(paymentCardToUpdate, request);
        updatedPaymentCard.setModifiedDate(LocalDateTime.now());
        paymentCardRepository.save(updatedPaymentCard);
        PaymentCardResponse response = paymentCardResponseMapper.mapFromRestToResponse(request);
        log.info("Payment card with id: {} successfully updated!", request.getId());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<SuccessResponse> deletePaymentCard(String paymentCardId) {
        PaymentCard paymentCard = paymentCardRepository.findById(new ObjectId(paymentCardId))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getDescription()));
        paymentCardRepository.delete(paymentCard);
        // todo: If user has any other payment card we should set user last created card as active!
        log.info("Payment card with id: {} successfully deleted!", paymentCardId);
        return ResponseEntity.ok(new SuccessResponse("Payment card successfully deleted: " + paymentCardId));
    }

    public ResponseEntity<SuccessResponse> activatePaymentCard(String paymentCardId) {
        PaymentCard paymentCardToActivate = paymentCardRepository.findById(new ObjectId(paymentCardId))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        deactivatePaymentCard();
        paymentCardToActivate.setCardActive(true);
        paymentCardToActivate.setModifiedDate(LocalDateTime.now());
        paymentCardRepository.save(paymentCardToActivate);
        return ResponseEntity.ok(new SuccessResponse("Payment card activated!"));
    }

    private void deactivatePaymentCard() {
        PaymentCard paymentCard = paymentCardRepository
                .findPaymentCardByUserIdAndAndCardActive(loggedUserProvider.loggedUser().getId().toHexString(), true)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        log.info("Found active payment card with id: {}", paymentCard.getId().toHexString());
        paymentCard.setCardActive(false);
        paymentCard.setModifiedDate(LocalDateTime.now());
        paymentCardRepository.save(paymentCard);
        log.info("Payment card with id: {} deactivated!", paymentCard.getId().toHexString());
    }

}
