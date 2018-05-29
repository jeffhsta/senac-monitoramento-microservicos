const rp = require( 'request-promise' );

const request = async nextService => {
  if ( !nextService ) { return {}; }

  return rp( {
    uri: `http://${nextService}/`,
    headers: { 'X-Request-Id': process.env.REQUEST_ID },
    json: true
  } );
};

module.exports = request;
