 const path = require('path');
 const CleanWebpackPlugin = require('clean-webpack-plugin');
 const HtmlWebpackPlugin = require('html-webpack-plugin');
 //const webpack = require('webpack');

 //TODO: experiment with configurations of webpack
 //Must get it working again!

 module.exports = {
   entry: {
     app: './src/main/js/app.js'
   },
       resolve: {
    alias: {
      'react-dom': '@hot-loader/react-dom'
    }
},
	    module:
	{
        rules: [
            	{
                test: /\.(js|jsx)$/,
                //path.resolve(__dirname, '.'),//
                exclude: /(node_modules)/,
                use: [
						            {
                    		    loader: 'babel-loader',
                    		      options: {
                        		      presets: ["@babel/preset-env", "@babel/preset-react"],
								                  plugins: ["@babel/plugin-proposal-class-properties",
                                  "react-hot-loader/babel"]
                    		          }
                		         }
					             ]
              },

              {
         		 	test: /\.css$/,
              //exclude: /(node_modules)/,
              loader: "style-loader!css-loader"
         		 	//use: ['style-loader']//, 'css-loader']
            }

        		]
    },
   plugins: [
     new CleanWebpackPlugin(),
     new HtmlWebpackPlugin({
       title: 'Dev',
       template: './src/main/resources/templates/index.html'}),
	     //new webpack.HotModuleReplacementPlugin()
   ],
   output: {
     filename: 'bundle.js',
     path: path.resolve(__dirname, 'dist')
   }
 };
