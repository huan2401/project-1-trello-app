const CracoAntDesignPlugin = require("craco-antd");
const path = require("path");

module.exports = {
    plugins: [
        {
            plugin: CracoAntDesignPlugin,
            options: {
                customizeTheme: {},
                customizeThemeLessPath: path.join(
                    __dirname,
                    "src/styles/customAntd.less"
                ),
            },
        },
    ],
};
