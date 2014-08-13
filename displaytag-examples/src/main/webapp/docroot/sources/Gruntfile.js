module.exports = function (grunt) {

  grunt.initConfig({

    pkg: grunt.file.readJSON('package.json'),

    watch: {
      sass: {
        files: ['sass/*.{scss,sass}', 'sass/**/*.{scss,sass}'],
        tasks: ['sass:dist']
      },
      iconizr: {
        files: [ '../img/svg/*'],
        tasks: ['iconizr:dist']
      }
    },
    sass: {
      dist: {
        options: {
          outputStyle: 'compressed',
          sourceComments: 'map'
        },
        files: {
          '../css/s-main.css': 'sass/s-main.scss'
        }
      }
    },
    iconizr: {
      options: {
        padding: 10,
        spritedir: '',
        keep: true,
        dims: false,
        level: 6,
        render: {
          css: false,
          scss: {
            dest: '../../sources/sass/partials/_svgsprite.scss',
             template    : 'sprite.scss.template'
          }
        },
        variables   : {
          png     : function() {
              return function(sprite, render) {
                  return render(sprite).split('.svg').join('.png');
              };
          }
        },
        cleanconfig: {
          plugins: [
            {
              moveGroupAttrsToElems: false
            },
            {
              convertPathData: false
            }
          ]
        },
        verbose: 1,
        layout: "vertical"
      },
      dist: {
        src:  '../img/svg/',
        dest  : '../img/sprite/'
      },
    }
  });

  require("matchdep").filterDev("grunt-*").forEach(grunt.loadNpmTasks);

  grunt.registerTask('default', ['iconizr', 'sass', 'watch']);

};