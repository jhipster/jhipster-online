import { Injectable } from '@angular/core';

import { GitConfigurationModel, GitProviderService } from 'app/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable({ providedIn: 'root' })
export class GitConfigurationService {
    gitConfig: GitConfigurationModel = {
        availableGitProviders: [],

        isGithubAvailable: false,
        isGithubConfigured: false,
        isAuthorizingGithub: false,
        githubClientId: '',
        githubHost: '',

        isGitlabAvailable: false,
        isGitlabConfigured: false,
        isAuthorizingGitlab: false,
        gitlabRedirectUri: '',
        gitlabClientId: '',
        gitlabHost: ''
    };

    private gitConfigSource = new BehaviorSubject(this.gitConfig);
    currentGitConfig = this.gitConfigSource.asObservable();

    constructor(public gitProviderService: GitProviderService) {}

    updateGitConfig(gitConfig: GitConfigurationModel) {
        this.gitConfigSource.next(gitConfig);
    }

    setGitConfiguration() {
        this.gitProviderService.getAvailableProviders().subscribe(providers => {
            if (providers.includes('github')) {
                this.gitProviderService.clientId('github').subscribe(clientId => (this.gitConfig.githubClientId = clientId));
                this.gitProviderService.getGithubConfig().subscribe(config => {
                    this.gitConfig.githubHost = config.host;
                });
                this.gitConfig.isGithubAvailable = true;
                this.gitConfig.availableGitProviders.push('github');
            }
            if (providers.includes('gitlab')) {
                this.gitProviderService.clientId('gitlab').subscribe(clientId => (this.gitConfig.gitlabClientId = clientId));
                this.gitProviderService.getGitlabConfig().subscribe(config => {
                    this.gitConfig.gitlabHost = config.host;
                    this.gitConfig.gitlabRedirectUri = config.redirectUri;
                });
                this.gitConfig.isGitlabAvailable = true;
                this.gitConfig.availableGitProviders.push('gitlab');
            }
        });
    }
}
