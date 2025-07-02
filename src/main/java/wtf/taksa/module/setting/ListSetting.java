package wtf.taksa.module.setting;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public class ListSetting extends Setting<Map<String, Boolean>> {
    
    public ListSetting(String name, String... options) {
        super(name, new LinkedHashMap<>());
        for (String option : options) {
            getValue().put(option, false);
        }
    }

    public boolean isToggled(String option) {
        return getValue().getOrDefault(option, false);
    }
    
    public void toggle(String option) {
        if (getValue().containsKey(option)) {
            setValue(option, !isToggled(option));
        }
    }

    public void setValue(String option, boolean toggled) {
        if (getValue().containsKey(option)) {
            getValue().put(option, toggled);
        }
    }
    
    public String getSummary() {
        long count = getValue().values().stream().filter(Boolean::booleanValue).count();
        if (count == 0) return "None";
        if (count > 2) return count + " selected";
        
        StringBuilder summary = new StringBuilder();
        for(Map.Entry<String, Boolean> entry : getValue().entrySet()) {
            if (entry.getValue()) {
                if (!summary.isEmpty()) summary.append(", ");
                summary.append(entry.getKey());
            }
        }
        return summary.toString();
    }
}