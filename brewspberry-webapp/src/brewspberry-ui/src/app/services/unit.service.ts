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
    }, {
      group: 'time',
      units: [
        {
          name: 'MILLISECONDS',
          multi: 1
        },
        {
          name: 'SECONDS',
          multi: 1000
        },
        {
          name: 'MINUTES',
          multi: 60000
        },
        {
          name: 'HOURS',
          multi: 3600000
        },
        {
          name: 'DAYS',
          multi: 43200000
        }
      ]
    }
  ];


  constructor() { }


  public convertTo(quantity: Quantity, unit: string): Quantity {
    let group = {};
    let fromUnit;
    let toUnit;
    console.log('Converting '+ quantity.quantity + ' '+ quantity.unit + ' to ' + unit);
    for (let unitType of this.units) {
      console.log(' > ' + unitType.group);
      for (let elt of unitType.units) {
        console.log(' >>> ' + elt.name + ' x' + elt.multi);
        if (elt.name === quantity.unit) {
          console.log('Found from : ' + elt.name);
          fromUnit = elt;
        }
        if (elt.name === unit) {
          console.log('Found to : ' + elt.name);
          toUnit = elt;
        }
        if (fromUnit && toUnit) {
          console.log('BREAK');
          break;
        }
      }
    }
    console.log('Return' + fromUnit.multi + ' '+ quantity.quantity + ' ' + toUnit.multi);
    console.log({quantity: fromUnit.multi * quantity.quantity * toUnit.multi, unit: toUnit.name});
    return {quantity: fromUnit.multi * quantity.quantity * toUnit.multi, unit: toUnit.name};
  }
}
