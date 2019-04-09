import { Services } from '@angular/core/src/view';

export class ChartData {
    series: Series;
}

export class Series extends Array<Serie> {
}

export class Serie {
    legend?: string;
    color?: string;
    points?: Array<Point>;
}

export class Point {
    x: number;
    y: number;
}