function setupProxy({ tls }) {
  const conf = [
    {
      context: ['/api', '/services', '/management', '/swagger-resources', '/v3/api-docs', '/h2-console', '/auth', '/jdl-studio'],
      target: `http${tls ? 's' : ''}://localhost:8080`,
      secure: false,
      changeOrigin: tls,
    },
  ];
  return conf;
}

module.exports = setupProxy;
