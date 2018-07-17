export class SidebarService {
    isShown = true;

    getIsShown() {
        return this.isShown;
    }

    toggleSidebar() {
        this.isShown = !this.isShown;
    }

    getSidebarStatus() {
        if (this.isShown) {
            return 'Hide';
        } else {
            return 'Show';
        }
    }
}
