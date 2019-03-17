import { DeviceType } from './device-type';
import { Pin } from './pin';

/**
 *
 */
export class Device {

    public genericId?: number = null;
    public id?: number;
    public name?: string;
    public uuid?: string;
    public type?: DeviceType;
    public externalIds?: Map<string, string>;
    public pin?: Pin;
    public isActive?: boolean;
    public state?: string;
    public plugged?: boolean;
    public temperature?: number;
}
