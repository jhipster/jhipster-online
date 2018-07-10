import { Injectable } from '@angular/core';

import { GitProviderService } from 'app/core';

@Injectable({ providedIn: 'root' })
export class GitConfigurationService {
    gitConfig: any = {
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

    constructor(public gitProviderService: GitProviderService) {}

    setGitConfiguration() {
        this.gitProviderService.getAvailableProviders().subscribe(providers => {
            if (providers.includes('github')) {
                this.gitProviderService.clientId('github').subscribe(clientId => (this.gitConfig.githubClientId = clientId));
                this.gitConfig.isAuthorizingGithub = true;
                this.gitConfig.isGithubConfigured = false;
                this.gitProviderService.getCompanies('github').subscribe(orgs => {
                    if (orgs.length === 0) {
                        this.gitProviderService.refreshGitProvider('github').subscribe(
                            () => (this.gitConfig.isGithubConfigured = true),
                            () => {
                                this.gitConfig.isAuthorizingGithub = false;
                                this.gitConfig.isGithubConfigured = false;
                            }
                        );
                    } else {
                        this.gitConfig.isGithubConfigured = true;
                    }
                });
                this.gitProviderService.getGithubConfig().subscribe(config => {
                    this.gitConfig.githubHost = config.host;
                });
                this.gitConfig.isGithubAvailable = true;
                this.gitConfig.availableGitProviders.push('github');
            }
            if (providers.includes('gitlab')) {
                this.gitProviderService.clientId('gitlab').subscribe(clientId => (this.gitConfig.gitlabClientId = clientId));
                this.gitConfig.isAuthorizingGitlab = true;
                this.gitConfig.isGitlabConfigured = false;
                this.gitProviderService.getCompanies('gitlab').subscribe(orgs => {
                    if (orgs.length === 0) {
                        this.gitProviderService.refreshGitProvider('gitlab').subscribe(
                            () => (this.gitConfig.isGitlabConfigured = true),
                            () => {
                                this.gitConfig.isAuthorizingGitlab = false;
                                this.gitConfig.isGitlabConfigured = false;
                            }
                        );
                    } else {
                        this.gitConfig.isGitlabConfigured = true;
                    }
                });
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
