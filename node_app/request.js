const rp = require( 'request-promise' );
const logger = require( './logger' );

const request = async nextService => {
  if ( !nextService ) { return {}; }

  const startTime = Date.now();
  const result = rp( {
    uri: `http://${nextService}/`,
    headers: { 'X-Request-Id': process.env.REQUEST_ID },
    json: true
  } );

  const time = Date.now() - startTime;
  logger.debug( { url: `http://${nextService}/`, time }, { type: 'http_client' } );

  return result;
};

module.exports = request;
