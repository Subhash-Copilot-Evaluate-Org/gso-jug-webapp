<#import "../layouts/application-base-layout.ftl" as layout>
<@layout.application_base_layout "GSO-JUG Opinion Poll">

    <hr/>

    <div class="container marketing">

        <div class="row featurette">
            <div class="col">
                <h2 class="featurette-heading">Opinion Poll</h2>
                <p class="lead">Share your opinion and see what the community thinks!</p>
            </div>
        </div>

        <#if poll??>
            <div id="pollDiv" class="bg-blue">
                <div class="row featurette">
                    <div class="col">
                        <h4>${poll.question}</h4>
                        <form id="pollForm">
                            <input type="hidden" id="pollId" value="${poll.id}">
                            <#list poll.options as option>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="pollOption"
                                           id="option_${option.id}" value="${option.id}">
                                    <label class="form-check-label" for="option_${option.id}">
                                        ${option.optionText}
                                    </label>
                                </div>
                            </#list>
                            <br/>
                            <div id="pollMessage" class="alert alert-warning" style="display: none;"></div>
                            <button type="submit" id="voteBtn" class="btn btn-primary">Submit Vote</button>
                        </form>
                    </div>
                </div>
            </div>

            <hr/>

            <div id="resultsDiv" class="bg-orange" style="display: none;">
                <div class="row featurette">
                    <div class="col">
                        <h4>Poll Results</h4>
                        <div id="pollResults"></div>
                        <p id="totalVotes" class="lead mt-3"></p>
                    </div>
                </div>
            </div>
        <#else>
            <div class="bg-blue">
                <div class="row featurette">
                    <div class="col">
                        <p class="lead">No active poll at this time. Please check back later.</p>
                    </div>
                </div>
            </div>
        </#if>

    </div>

</@layout.application_base_layout>
