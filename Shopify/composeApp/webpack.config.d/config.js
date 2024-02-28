const TerserPlugin = require("terser-webpack-plugin");

config.optimization = config.optimization || {};
config.optimization.minimize = true;
config.devServer.host = "192.168.14.2";
config.devServer.port = 8080;
config.mode = "development";

config.optimization.minimizer = [
    new TerserPlugin({
        terserOptions: {
            mangle: true,    // Note: By default, mangle is set to true.
            compress: false, // Disable the transformations that reduce the code size.
            output: {
                beautify: false,
            },

            
        },
    }),

];

