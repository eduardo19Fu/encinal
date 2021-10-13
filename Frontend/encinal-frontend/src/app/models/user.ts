import { Role } from './role';

export class User{

    userId: number;
    username: string;
    password: string;
    firstName: string;
    middleName: string;
    lastName: string;
    enabled: boolean;
    createdAt: Date;
    roles: string[] = [];
}
