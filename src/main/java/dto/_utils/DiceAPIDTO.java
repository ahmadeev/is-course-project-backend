package dto._utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiceAPIDTO {
    // {
    //      "input":"1d20",
    //      "result":19,
    //      "details":"19",
    //      "code":"",
    //      "illustration":"<span style=\"color: gray;\"><\/span> <span class=\"dc_dice_a\"><\/span><span class=\"dc_dice_d\">D20<\/span>",
    //      "timestamp":1743251383,
    //      "x":1743251383
    // }

    private String input;
    private int result;
    private String details;
    private String code;
    private String illustration;
    private long timestamp;
    private long x;
}
