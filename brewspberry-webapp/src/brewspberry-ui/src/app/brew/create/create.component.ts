import { Component, OnInit, OnChanges, ViewChild, ComponentRef, ViewContainerRef, Input, ComponentFactoryResolver } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InputsComponent } from '../steps/inputs/inputs.component';
import { MashingComponent } from '../steps/mashing/mashing.component';
import { FilteringComponent } from '../steps/filtering/filtering.component';
import { BoilingComponent } from '../steps/boiling/boiling.component';
import { FermentationComponent } from '../steps/fermentation/fermentation.component';
import { SecondFermentationComponent } from '../steps/second-fermentation/second-fermentation.component';
import { ConditioningComponent } from '../steps/conditioning/conditioning.component';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit, OnChanges {


  @ViewChild('target', { read: ViewContainerRef})
  dynamicView;
  @Input()
  private dynamicViewCmpRef: ComponentRef<any>;

  private inputsComponent: InputsComponent;
  private mashingComponent: MashingComponent;
  private filteringComponent: FilteringComponent;
  private boilingComponent: BoilingComponent;
  private fermentationComponent: FermentationComponent;
  private secondFermentationComponent: SecondFermentationComponent;
  private conditioningComponent: ConditioningComponent;
  
  constructor(private route: ActivatedRoute, private resolver: ComponentFactoryResolver) { }

  ngOnInit() {
  		this.route.params.subscribe(params => {
 			this.routeToView(params['selectedStep']);
	  	});
  }

  ngOnChanges() {
  }


  routeToView(subViewForStep: string){
  	
  		this.dynamicView ? this.dynamicView.clear() : '';
		const factory: ComponentFactory = null;
  		switch (subViewForStep) {
  			case "inputs":
  				 factory = this.resolver.resolveComponentFactory(InputsComponent);
  				break;
  			case "mashing":
  				 factory = this.resolver.resolveComponentFactory(MashingComponent);
  				break;
  			case "filtering":
  				 factory = this.resolver.resolveComponentFactory(FilteringComponent);
  				break;
  			case "boiling":
  				 factory = this.resolver.resolveComponentFactory(BoilingComponent);
  				break;
  			case "fermentation":
  				 factory = this.resolver.resolveComponentFactory(FermentationComponent);
  				break;
  			case "second-fermentation":
  				 factory = this.resolver.resolveComponentFactory(SecondFermentationComponent);
  				break;
  			case "conditioning":
  				 factory = this.resolver.resolveComponentFactory(ConditioningComponent);
  				break;
  			
  			default:
  				 factory = this.resolver.resolveComponentFactory(InputsComponent);
  				break;
  		}
  		 this.dynamicViewCmpRef = this.dynamicView.createComponent(factory);

  }

}
