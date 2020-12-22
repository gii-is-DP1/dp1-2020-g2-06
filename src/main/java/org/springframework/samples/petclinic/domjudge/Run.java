
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
    "run_time",
    "time",
    "contest_time",
    "ordinal",
    "id",
    "judgement_id",
    "judgement_type_id"
})
public class Run {

    @JsonProperty("run_time")
    private Double runTime;
    @JsonProperty("time")
    private String time;
    @JsonProperty("contest_time")
    private String contestTime;
    @JsonProperty("ordinal")
    private Integer ordinal;
    @JsonProperty("id")
    private String id;
    @JsonProperty("judgement_id")
    private String judgementId;
    @JsonProperty("judgement_type_id")
    private String judgementTypeId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("run_time")
    public Double getRunTime() {
        return runTime;
    }

    @JsonProperty("run_time")
    public void setRunTime(Double runTime) {
        this.runTime = runTime;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("contest_time")
    public String getContestTime() {
        return contestTime;
    }

    @JsonProperty("contest_time")
    public void setContestTime(String contestTime) {
        this.contestTime = contestTime;
    }

    @JsonProperty("ordinal")
    public Integer getOrdinal() {
        return ordinal;
    }

    @JsonProperty("ordinal")
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("judgement_id")
    public String getJudgementId() {
        return judgementId;
    }

    @JsonProperty("judgement_id")
    public void setJudgementId(String judgementId) {
        this.judgementId = judgementId;
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
