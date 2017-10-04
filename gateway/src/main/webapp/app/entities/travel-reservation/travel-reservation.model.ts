import { BaseEntity } from './../../shared';

export class TravelReservation implements BaseEntity {
    constructor(
        public id?: number,
        public tourCode?: string,
        public numberOfPersons?: number,
        public needVisa?: boolean,
        public clientConfirmed?: boolean,
        public providerConfirmed?: boolean,
        public agencyConfirmed?: boolean,
        public notes?: string,
    ) {
        this.needVisa = false;
        this.clientConfirmed = false;
        this.providerConfirmed = false;
        this.agencyConfirmed = false;
    }
}
