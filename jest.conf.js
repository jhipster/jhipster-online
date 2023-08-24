const { pathsToModuleNameMapper } = require('ts-jest');

const {
  compilerOptions: { paths = {}, baseUrl = './' }
} = require('./tsconfig.json');
const environment = require('./webpack/environment');

module.exports = {
  transformIgnorePatterns: ['node_modules/(?!.*\\.mjs$|dayjs/esm)'],
  testRunner: 'jest-jasmine2',
  setupFiles: ['jest-date-mock'],
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
  testMatch: ['<rootDir>/src/test/javascript/spec/**/@(*.)@(spec.ts)'],
  testEnvironmentOptions: {
    url: 'http://localhost/'
  },
  roots: ['<rootDir>', `<rootDir>/${baseUrl}`]
};
