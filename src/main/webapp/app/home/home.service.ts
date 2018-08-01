export class HomeService {
    fullScreen = false;

    isFullScreen() {
        return this.fullScreen;
    }

    toggleFullScreen() {
        this.fullScreen = !this.fullScreen;
    }

    exitFullScreen() {
        this.fullScreen = false;
    }
}
