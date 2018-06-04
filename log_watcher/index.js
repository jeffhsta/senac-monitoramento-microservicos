const Hapi = require( 'hapi' );

const server=Hapi.server( {
  host: '0.0.0.0',
  port: 8000
} );

server.route( {
  method: 'POST',
  path: '/log',
  handler: function( req, h ) {
    if ( req.payload.metadata.type === 'http_server' && req.payload.content.status_code === 500 ) {
      console.log( `[ALERT] ${req.payload.metadata.source} got a 500` );
    }
    return {};
  }
} );

async function start() {

  try {
    await server.start();
  }
  catch (err) {
    console.log(err);
    process.exit(1);
  }

  console.log('Server running at:', server.info.uri);
};

start();
