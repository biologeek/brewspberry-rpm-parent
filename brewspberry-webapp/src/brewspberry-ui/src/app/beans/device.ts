
/**
 * 
 * 
 */
export class Device {

	public genericId?: number = null;
	public id: number;
	public name?: string;
	public uuid?: string;
	public type?: string;
	public externalIds?: Map<string, string>;
	public pin?: string;
	public isActive?: boolean;
	public state?: string;
	public plugged?: boolean;
	public temperature?: string;
}
