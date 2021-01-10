
package org.springframework.samples.petclinic.domjudge;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "max_run_time",
    "start_time",
    "start_contest_time",
    "end_time",
    "end_contest_time",
    "id",
    "submission_id",
    "valid",
    "judgehost",
    "judgement_type_id"
})
public class Judgement {

    @JsonProperty("max_run_time")
    private Double maxRunTime;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("start_contest_time")
    private String startContestTime;
    @JsonProperty("end_time")
    private String endTime;
    @JsonProperty("end_contest_time")
    private String endContestTime;
    @JsonProperty("id")
    private String id;
    @JsonProperty("submission_id")
    private String submissionId;
    @JsonProperty("valid")
    private Boolean valid;
    @JsonProperty("judgehost")
    private String judgehost;
    @JsonProperty("judgement_type_id")
    private String judgementTypeId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("max_run_time")
    public Double getMaxRunTime() {
        return maxRunTime;
    }

    @JsonProperty("max_run_time")
    public void setMaxRunTime(Double maxRunTime) {
        this.maxRunTime = maxRunTime;
    }

    @JsonProperty("start_time")
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty("start_time")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("start_contest_time")
    public String getStartContestTime() {
        return startContestTime;
    }

    @JsonProperty("start_contest_time")
    public void setStartContestTime(String startContestTime) {
        this.startContestTime = startContestTime;
    }

    @JsonProperty("end_time")
    public String getEndTime() {
        return endTime;
    }

    @JsonProperty("end_time")
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonProperty("end_contest_time")
    public String getEndContestTime() {
        return endContestTime;
    }

    @JsonProperty("end_contest_time")
    public void setEndContestTime(String endContestTime) {
        this.endContestTime = endContestTime;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("submission_id")
    public String getSubmissionId() {
        return submissionId;
    }

    @JsonProperty("submission_id")
    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    @JsonProperty("valid")
    public Boolean getValid() {
        return valid;
    }

    @JsonProperty("valid")
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @JsonProperty("judgehost")
    public String getJudgehost() {
        return judgehost;
    }

    @JsonProperty("judgehost")
    public void setJudgehost(String judgehost) {
        this.judgehost = judgehost;
    }

    @JsonProperty("judgement_type_id")
    public String getJudgementTypeId() {
        return judgementTypeId;
    }

    @JsonProperty("judgement_type_id")
    public void setJudgementTypeId(String judgementTypeId) {
        this.judgementTypeId = judgementTypeId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
