const { pathsToModuleNameMapper } = require('ts-jest');

const {
  compilerOptions: { paths = {}, baseUrl = './' }
} = require('./tsconfig.json');
const environment = require('./webpack/environment');

module.exports = {
  testRunner: 'jest-jasmine2',
  preset: 'jest-preset-angular',
  setupFiles: ['jest-date-mock'],
  setupFilesAfterEnv: ['<rootDir>/src/test/javascript/jest.ts'],
  cacheDirectory: '<rootDir>/target/jest-cache',
  coverageDirectory: '<rootDir>/target/test-results/',
  globals: {
    ...environment,
    'ts-jest': {
      stringifyContentPathRegex: '\\.html$',
      tsconfig: '<rootDir>/tsconfig.spec.json'
    }
  },
  coveragePathIgnorePatterns: ['<rootDir>/src/test/javascript'],
  moduleNameMapper: pathsToModuleNameMapper(paths, { prefix: `<rootDir>/${baseUrl}/` }),
  reporters: ['default', ['jest-junit', { outputDirectory: './target/test-results/', outputName: 'TESTS-results-jest.xml' }]],
  testResultsProcessor: 'jest-sonar-reporter',
  transformIgnorePatterns: ['node_modules/'],
  testMatch: ['<rootDir>/src/test/javascript/spec/**/@(*.)@(spec.ts)'],
  testURL: 'http://localhost/',
  roots: ['<rootDir>', `<rootDir>/${baseUrl}`]
};
