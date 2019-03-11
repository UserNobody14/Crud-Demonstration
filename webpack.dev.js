 const merge = require('webpack-merge');
 const common = require('./webpack.common.js');
 const path = require('path');
 //const webpack = require('webpack')
 //const ExtractCssChunks = require("extract-css-chunks-webpack-plugin")

 module.exports = merge(common, {
   mode: 'development',
   devtool: 'inline-source-map',
   /*module: {
     rules: [{
         test: /\.(js|jsx)$/,
         exclude: /(node_modules)/,
         use: [{
           loader: 'babel-loader',
           options: {
             presets: ["@babel/preset-env", "@babel/preset-react"],
             plugins: ["react-hot-loader/babel"]
           }
         }]
       },
       {
         test: /\.css$/,
         //exclude: /node_modules/,
         use: ['style-loader', 'css-loader']
       }
     ]
     rules: [
      {
        test: /\.css$/,
        use: [
           {
             loader:ExtractCssChunks.loader,
             options: {
               hot: true, // if you want HMR - we try to automatically inject hot reloading but if it's not working, add it to the config
               modules: true, // if you use cssModules, this can help.
               reloadAll: true, // when desperation kicks in - this is a brute force HMR flag

             }
           },
           "css-loader"
         ]
      }
    ]
   },
     plugins: [
    new ExtractCssChunks(
        {
          // Options similar to the same options in webpackOptions.output
          // both options are optional
          filename: "[name].css",
          chunkFilename: "[id].css",
          orderWarning: true, // Disable to remove warnings about conflicting order between imports
        }
    ),
  ]
  */
   devServer: {
     contentBase: [
       path.join(__dirname, '/src/main/resources/static'),
       path.join(__dirname, '/src/main/resources/templates')]
       //path.resolve(__dirname, 'dist')]'
   }
 });
 //http://localhost:9000/webpack-dev-server.
