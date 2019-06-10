import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'camelcase'
})
export class CamelcasePipe implements PipeTransform {

  transform(value: string, args?: any): any {
    let res = "";

    if (!value) {
      return res;
    }
    if (value.length > 1){
      return `${value.substring(0,1).toUpperCase()}${value.substring(1).toLowerCase()}`;
    }
    return value.toUpperCase();
  }

}
