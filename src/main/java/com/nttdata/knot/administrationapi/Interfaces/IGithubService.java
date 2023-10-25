package com.nttdata.knot.administrationapi.Interfaces;

import java.util.List;

import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubBranch;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubCommits.GetGithubCommitsResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.CreateGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.DeleteGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse.GetGithubFileResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.GetGithubTagResponse;
import com.nttdata.knot.administrationapi.Models.UserPackage.GetUser;

import reactor.core.publisher.Mono;

public interface IGithubService {
    Mono<GetGithubFileResponse> getGithubFileAsync(String repoName, String name);

    Mono<String> createGithubFileAsync(CreateGithubFileRequest createGithubFile, String repoName, String name);

    Mono<String> deleteGithubFileAsync(DeleteGithubFileRequest deleteGithubFile, String repoName, String name);

    Mono<String> createGithubTagAsync(String repoName, String source, String target, String message);

    Mono<List<GetGithubCommitsResponse>> getGithubCommitsAsync(String repoName);

    Mono<List<GetGithubTagResponse>> getGithubTagAsync(String repoName);

    Mono<List<GetUser>> getGithubUserList();

    Mono<List<String>> listDevContainers();

    Mono<List<GithubBranch>> getGithubBranches(String repoName, String userToken);
}
