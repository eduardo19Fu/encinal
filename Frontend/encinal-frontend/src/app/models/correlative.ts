import { UserAuxiliar } from './user-auxiliar';
import { Status } from './status';

export class Correlative {

    correlativeId: number;
    initialCorrelative: number;
    finalCorrelative: number;
    currentCorrelative: number;
    createdAt: Date;

    createdBy: UserAuxiliar;
    assignedTo: UserAuxiliar;
    status: Status;
}
