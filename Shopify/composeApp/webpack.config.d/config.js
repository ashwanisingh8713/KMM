const TerserPlugin = require("terser-webpack-plugin");

config.optimization = config.optimization || {};
config.optimization.minimize = true;
if(config.mode == "development") {
    config.devServer.host = "192.168.14.2";
    config.devServer.port = 8080;

    config.devServer.headers = {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS",
        "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With",
        "Access-Control-Expose-Headers": "Content-Range, X-Content-Range, X-Total-Count, X-Page-Count, X-Page-Number, X-Page-Size, X-Page-Offset",
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Max-Age": "86400"
        };

    config.devServer.proxy = {
        '/api': {
            target: 'http://localhost:8081',
            pathRewrite: {'^/api' : ''},
            secure: false,
            changeOrigin: true,
        },
    };
}




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

