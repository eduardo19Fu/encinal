import { Role } from './role';

export class UserAuxiliar{

    userId: number;
    username: string;
    email: string;
    password: string;
    firstName: string;
    middleName: string;
    lastName: string;
    enabled: boolean;
    createdAt: Date;

    roles: Role[] = [];
}
