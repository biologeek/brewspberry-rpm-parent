import {
    Component, OnInit, OnChanges, ViewChild, ComponentRef, ViewContainerRef, Input,
    ComponentFactoryResolver, ComponentFactory, Output, EventEmitter
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InputsComponent } from '../steps/inputs/inputs.component';
import { MashingComponent } from '../steps/mashing/mashing.component';
import { FilteringComponent } from '../steps/filtering/filtering.component';
import { BoilingComponent } from '../steps/boiling/boiling.component';
import { FermentationComponent } from '../steps/fermentation/fermentation.component';
import { SecondFermentationComponent } from '../steps/second-fermentation/second-fermentation.component';
import { ConditioningComponent } from '../steps/conditioning/conditioning.component';
import { Brew } from 'src/app/beans/brewery/brew';


@Component({
    selector: 'app-create',
    templateUrl: './create.component.html',
    styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit, OnChanges {


    @ViewChild('target', { read: ViewContainerRef })
    dynamicView: ViewContainerRef;
    @Input()
    private dynamicViewCmpRef: ComponentRef<any>;
    selectedStep: string;
    private availableViews = ['inputs', 'mashing', 'filtering', 'boiling', 'fermentation', 'second-fermentation', 'conditioning'];

    inputsComponent: InputsComponent;
    mashingComponent: MashingComponent;
    filteringComponent: FilteringComponent;
    boilingComponent: BoilingComponent;
    fermentationComponent: FermentationComponent;
    secondFermentationComponent: SecondFermentationComponent;
    conditioningComponent: ConditioningComponent;

    private currentBrew: Brew = {};
    private availableMalts = [{ name: 'Marris Otter' }, { name: 'Carapils' }];

    constructor(private route: ActivatedRoute, private router: Router, private resolver: ComponentFactoryResolver) { }

    ngOnInit() {
        this.currentBrew = { id: 1 };
        this.route.params.subscribe(params => {
            this.routeToView(params['selectedStep']);
        });
    }

    ngOnChanges() {
    }


    routeToView(subViewForStep: string) {
        this.router.navigate([`brew/create/${subViewForStep}`]);
        console.log(this.availableViews[this.availableViews.indexOf(this.selectedStep) - 1]);
        if (this.dynamicView) {
            this.dynamicView.clear();
        }
        let factory = null;
        this.selectedStep = subViewForStep;
        switch (subViewForStep) {
            case 'inputs':
                factory = this.resolver.resolveComponentFactory(InputsComponent);
                break;
            case 'mashing':
                factory = this.resolver.resolveComponentFactory(MashingComponent);
                break;
            case 'filtering':
                factory = this.resolver.resolveComponentFactory(FilteringComponent);
                break;
            case 'boiling':
                factory = this.resolver.resolveComponentFactory(BoilingComponent);
                break;
            case 'fermentation':
                factory = this.resolver.resolveComponentFactory(FermentationComponent);
                break;
            case 'second-fermentation':
                factory = this.resolver.resolveComponentFactory(SecondFermentationComponent);
                break;
            case 'conditioning':
                factory = this.resolver.resolveComponentFactory(ConditioningComponent);
                break;

            default:
                factory = this.resolver.resolveComponentFactory(InputsComponent);
                this.selectedStep = 'inputs';
                break;
        }
        this.dynamicViewCmpRef = this.dynamicView.createComponent(factory);
    }

}
