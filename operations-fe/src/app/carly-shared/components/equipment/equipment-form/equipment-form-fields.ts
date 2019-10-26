import {FormGroupHelper} from "../../../model/form-group-helper.model";
import {Validators} from "@angular/forms";
import {Equipment} from "../../../model/equipment.model";

export const equipmentFormFields: FormGroupHelper.Model[] = [
  {
    inputName: 'partName',
    label: 'Name',
    validators: [Validators.required],
    type: 'text',
    cols: 4,
    rows: 1
  },
  {
    inputName: 'partBrand',
    label: 'Brand',
    validators: [Validators.required],
    type: 'select',
    cols: 4,
    rows: 1,
    selectOptions: []
  },
  {
    inputName: 'type',
    label: 'Type',
    validators: [Validators.required],
    type: 'select',
    cols: 4,
    rows: 1,
    selectOptions: [...Object.values(Equipment.EquipmentType).map(value => ({label: value, value}))]
  },
  {
    inputName: 'partPrice',
    label: 'Price',
    validators: [Validators.required],
    type: 'number',
    cols: 4,
    rows: 1
  }
];
