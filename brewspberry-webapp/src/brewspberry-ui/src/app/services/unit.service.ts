import { Injectable } from '@angular/core';
import { Quantity } from '../beans/quantity';


/**
 * Unit conversion service
 */
@Injectable({
  providedIn: 'root'
})
export class UnitService {

  units = [
    {
      group: 'volume',
      units: [
        {
          name: 'MILLILITER',
          multi: 0.001
        },
        {
          name: 'CENTILITER',
          multi: 0.01
        },
        {
          name: 'LITER',
          multi: 1
        }
      ]
    }, {
      group: 'mass',
      units: [
        {
          name: 'MILIGRAM',
          multi: 0.001
        },
        {
          name: 'GRAM',
          multi: 1
        },
        {
          name: 'KILOGRAM',
          multi: 1000
        }
      ]
    }, {
      group: 'distance',
      units: [
        {
          name: 'MILLIMETER',
          multi: 0.001
        },
        {
          name: 'KILOMETER',
          multi: 1000
        },
        {
          name: 'CENTIMETER',
          multi: 0.01
        },
        {
          name: 'METER',
          multi: 1
        }
      ]
    }
  ];


  constructor() { }


  public convertTo(quantity: Quantity, unit: string): Quantity {
    let group = {};
    let fromUnit;
    let toUnit;
    for (let unitType of this.units) {
      for (let elt of unitType.units) {
        if (elt.name === quantity.unit) {
          fromUnit = elt;
        }
        if (elt.name === unit) {
          toUnit = elt;
        }
        if (fromUnit && toUnit) {
          break;
        }
      }
      return {quantity: fromUnit * quantity.quantity * toUnit, unit: toUnit};
    }
  }
}
