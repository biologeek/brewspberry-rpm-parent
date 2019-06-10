import { Pipe, PipeTransform } from '@angular/core';
import { UnitService } from './services/unit.service';

@Pipe({
  name: 'quantity'
})
export class QuantityPipe implements PipeTransform {

  constructor(private unitService: UnitService) {}

  transform(value: any, args?: any): any {
    const shortNames = {
      'MILLISECONDS': 'ms',
      'SECONDS': 's',
      'MINUTES': 'min',
      'HOURS': 'h',
      'DAYS': 'days',
      'MONTHS': 'months',
      'YEARS': 'years'
    };
    if (!value)
      return `Ø`;
    
    return `${value.quantity} ${shortNames[value.unit]}`;
  }

}
