import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Brew } from '../../../beans/brewery/brew';
import { Ingredient } from '../../../beans/ingredient';
import { IngredientService } from '../../../services/ingredient.service';
import * as _ from 'lodash';

@Component({
	selector: 'app-inputs',
	templateUrl: './inputs.component.html',
	styleUrls: ['./inputs.component.css']
})
export class InputsComponent implements OnInit {

	currentBrew: Brew = { malts: null }; //{malts: [{id: 1, model: 'Marris Otter'}]};
	nextMalt: Ingredient = {};
	nextHop: Ingredient = {};
	nextYeast: Ingredient = {};
	nextSpice: Ingredient = {};
	currentlyModifiedMalt: Ingredient = {};
	currentlyModifiedHop: Ingredient = {};
	currentlyModifiedYeast: Ingredient = {};
	currentlyModifiedSpice: Ingredient = {};
	formatter;
	availableMalts: Ingredient[] = [{ id: 1, model: 'Marris Otter' }, { id: 2, model: 'Carapils' }, { id: 3, model: 'Café' }];
	availableHops: Ingredient[] = [{ id: 1, model: 'MNelson Sauvin' }, { id: 2, model: 'Saaz' }, { id: 3, model: 'Blabla' }];
	availableYeasts: Ingredient[] = [{ id: 1, model: 'Marris Otter' }, { id: 2, model: 'Carapils' }, { id: 3, model: 'Café' }];
	availableSpices: Ingredient[] = [{ id: 1, model: 'Cannelle' }, { id: 2, model: 'Cumin' }, { id: 3, model: 'Réglisse' }];


	@Output()
	modifiedBrew = new EventEmitter<Brew>();

	constructor(private toast: ToastrService, private ingService: IngredientService) { }

	ngOnInit() {

		this.ingService.listAvailableMalts().subscribe(data => {
			this.availableMalts = data;
		}, error => {
			this.toast.error('Service malt indisponible');
		});
		this.ingService.listAvailableHops().subscribe(data => {
			this.availableHops = data;
		}, error => {
			this.toast.error('Service houblon indisponible');
		});

		this.ingService.listAvailableYeasts().subscribe(data => {
			this.availableYeasts = data;
		}, error => {
			this.toast.error('Service levure indisponible');
		});

		this.ingService.listAvailableSpices().subscribe(data => {
			this.availableSpices = data;
		}, error => {
			this.toast.error('Service épices indisponible');
		})
	}


	appendToList(): void {
		if (this.nextMalt && this.nextMalt.quantity) {
			if (!this.currentBrew.malts) {
				this.currentBrew.malts = [];
			}
			for (let malt of this.availableMalts) {
				if (malt.model == this.nextMalt.model) {
					let toInsert = _.cloneDeep(malt);
					toInsert.quantity = this.nextMalt.quantity;
					this.currentBrew.malts.push(toInsert);
					this.nextMalt = {};
				}
			}
			this.toast.success('Malt sauvegardé !');
		} else if (this.nextHop && this.nextHop.quantity) {
			if (!this.currentBrew.hops) {
				this.currentBrew.hops = [];
			}
			for (let hop of this.availableHops) {
				if (hop.model == this.nextHop.model) {
					let toInsert = _.cloneDeep(hop);
					toInsert.quantity = this.nextHop.quantity;
					this.currentBrew.hops.push(toInsert);
					this.nextHop = {};
				}
			}
			this.toast.success('Houblon sauvegardé !');
		} else if (this.nextYeast && this.nextYeast.quantity) {
			if (!this.currentBrew.yeasts) {
				this.currentBrew.yeasts = [];
			}
			for (let yeast of this.availableYeasts) {
				if (yeast.model == this.nextYeast.model) {
					let toInsert = _.cloneDeep(yeast);
					toInsert.quantity = this.nextYeast.quantity;
					this.currentBrew.yeasts.push(toInsert);
					this.nextYeast = {};
				}
			}
			this.toast.success('Levure sauvegardée !');
		} else if (this.nextSpice && this.nextSpice.quantity) {
			if (!this.currentBrew.yeasts) {
				this.currentBrew.yeasts = [];
			}
			for (let yeast of this.availableSpices) {
				if (yeast.model == this.nextSpice.model) {
					let toInsert = _.cloneDeep(yeast);
					toInsert.quantity = this.nextSpice.quantity;
					this.currentBrew.yeasts.push(toInsert);
					this.nextSpice = {};
				}
			}
			this.toast.success('Levure sauvegardée !');
		}
	}

	update(oldModifiedMalt) {
		let currentlyModified;
		let available;
		let toAddTo;
		if (this.currentlyModifiedMalt) {
			currentlyModified = this.currentlyModifiedMalt;
			available = this.availableMalts;
			toAddTo = this.currentBrew.malts;
		} else if (this.currentlyModifiedHop) {
			currentlyModified = this.currentlyModifiedHop;
			available = this.availableHops;
			toAddTo = this.currentBrew.hops;
		} else if (this.currentlyModifiedYeast) {
			currentlyModified = this.currentlyModifiedYeast;
			available = this.availableYeasts;
			toAddTo = this.currentBrew.yeasts;
		} else if (this.currentlyModifiedSpice) {
			currentlyModified = this.currentlyModifiedSpice;
			available = this.availableSpices;
			toAddTo = this.currentBrew.spices;
		}
		// Get from maltList
		for (let ingredient of available) {
			if (ingredient.model == currentlyModified.model) {
				let temp = toAddTo.slice();

				for (let i in temp) {
					let toModifyMalt = temp[i];
					if (oldModifiedMalt.id === toModifyMalt.id) {
						toAddTo[i] = ingredient;
						toAddTo[i].quantity = currentlyModified.quantity;
						if (this.currentlyModifiedMalt) {
							this.currentlyModifiedMalt = {};
						} else if (this.currentlyModifiedHop) {
							this.currentlyModifiedHop = {};
						} else if (this.currentlyModifiedYeast) {
							this.currentlyModifiedYeast = {};
						}
					}
				}
			}
		}
	}

	editMalt(malt) {
		Object.assign(this.currentlyModifiedMalt, malt);
	}




}
