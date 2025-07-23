package wtf.taksa.common.functions.storage.misc;

import lombok.Getter;
import wtf.taksa.common.functions.Function;
import wtf.taksa.common.functions.FunctionCategory;
import wtf.taksa.common.functions.FunctionRegistry;
import wtf.taksa.common.functions.settings.impl.StringSetting;

@FunctionRegistry(name = "ClientBrand", description = "Подменяет clientbrand при заходе на сервер.", category = FunctionCategory.MISC)
public class ClientBrand extends Function {
    @Getter
    public static ClientBrand instance = new ClientBrand();
    public StringSetting setting = new StringSetting("ClientBrand", "vanilla");

    public ClientBrand() {
        addSetting(setting);
    }
}
