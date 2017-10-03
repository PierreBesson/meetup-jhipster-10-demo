import { BaseEntity } from './../../shared';

export class Tour implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public numberOfDays?: number,
        public numberOfNights?: number,
        public description?: string,
        public timeOfDeparture?: any,
        public timeOfReturn?: any,
        public price?: number,
        public capacity?: number,
    ) {
    }
}
