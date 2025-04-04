package responses;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import response.ResponseStatus;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity {
    private ResponseStatus status;
    private String details;
    private Object data;

    public String toJson() {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
