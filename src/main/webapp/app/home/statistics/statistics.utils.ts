import { Injectable } from '@angular/core';

const displayNames = {
    react: 'React',
    angularX: 'Angular',
    aws: 'AWS',
    cloudfoundry: 'Cloud Foundry',
    openshift: 'OpenShift',
    postgresql: 'PostgreSQL',
    mysql: 'MySQL',
    mariadb: 'MariaDB',
    no: 'None'
};

@Injectable({ providedIn: 'root' })
export class StatisticsUtils {
    public static getDisplayName(name: string, upperFirst = true) {
        return upperFirst ? StatisticsUtils.upperFirst(displayNames[name] || name) : displayNames[name] || name;
    }

    public static upperFirst(text: string) {
        return text.charAt(0).toUpperCase() + text.slice(1);
    }
}
