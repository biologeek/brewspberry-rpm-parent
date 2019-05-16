import { Component, OnInit } from '@angular/core';
import { Step } from 'src/app/beans/brewery/step';
import { Brew } from 'src/app/beans/brewery/brew';
import { BrewService } from 'src/app/services/brew.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { IngredientService } from 'src/app/services/ingredient.service';
import { Ingredient } from 'src/app/beans/brewery/ingredient';

@Component({
  selector: 'app-brew-detail',
  templateUrl: './brew-detail.component.html',
  styleUrls: ['./brew-detail.component.css']
})
export class BrewDetailComponent implements OnInit {

  currentStep: Step = new Step();
  brew: Brew;
  ingredientsList: Ingredient[];
  brewId: number;

  subs$: Subscription[] = [];

  constructor(private route: ActivatedRoute, private brewService: BrewService, private ingredientService: IngredientService) { }

  ngOnInit() {
    this.subs$.push(this.route.params.subscribe(prm => {
      this.brewId = prm['id'];
      this.subs$.push(this.ingredientService.getAllIngredients().subscribe(res => {
        this.ingredientsList = res;
      }))
      const brewSub$ = this.brewService.getBrew(this.brewId).subscribe(brw => {
        this.brew = brw;
        this.dispatchBrewQuantities();
      });
      this.subs$.push(brewSub$);
    }));
  }

  private dispatchBrewQuantities() {
    const brewQuantities = this.calculateBrewQuantities();
    const ingredients = [...this.brew.malts, ...this.brew.spices, ...this.brew.yeasts, ...this.brew.additives, ...this.brew.hops];
    for(let bq of brewQuantities) {
        ingredients.filter(ing => ing.id === bq.id)[0].quantity = bq.quantity;
    }
  }

  private calculateBrewQuantities() {
    const finalQuantities = [];
    for (let step of this.brew.steps) {
      for (let ing of step.ingredients) {
        const possible = finalQuantities.filter(s => s.id === ing.ingredient);
        if (possible.length == 1) {
          possible[0].quantity.quantity += ing.quantityAdded.quantity;
        } else {
          finalQuantities.push({
            id: ing.ingredient,
            quantity: ing.quantityAdded
          })
        }
      }
    }
    return finalQuantities;
  }
}
