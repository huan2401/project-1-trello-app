// const CracoLessPlugin = require('craco-less');
//
// module.exports = {
//     plugins: [
//         {
//             plugin: CracoLessPlugin,
//             options: {
//                 lessLoaderOptions: {
//                     lessOptions: {
//                         modifyVars: { '@primary-color': '#1DA57A' },
//                         javascriptEnabled: true,
//                     },
//                 },
//             },
//         },
//     ],
// };

const CracoAntDesignPlugin = require("craco-antd");
const path = require("path");

module.exports = {
    plugins: [
        {
            plugin: CracoAntDesignPlugin,
            options: {
                customizeTheme: {
                },
                customizeThemeLessPath: path.join(
                  __dirname,
                  "src/styles/customAntd.less"
                ),
            },
        },
    ],
};