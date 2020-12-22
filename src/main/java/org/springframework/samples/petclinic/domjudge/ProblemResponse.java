
package org.springframework.samples.petclinic.domjudge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "problem_ids",
    "messages"
})
public class ProblemResponse {

    @JsonProperty("problem_ids")
    private List<Integer> problemIds = null;
    @JsonProperty("messages")
    private List<String> messages = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("problem_ids")
    public List<Integer> getProblemIds() {
        return problemIds;
    }

    @JsonProperty("problem_ids")
    public void setProblemIds(List<Integer> problemIds) {
        this.problemIds = problemIds;
    }

    @JsonProperty("messages")
    public List<String> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<String> messages) {
        this.messages = messages;
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
