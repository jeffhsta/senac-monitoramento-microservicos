const express = require( 'express' );
const app = express();
const addRequestId = require('express-request-id')();
const logger = require( './logger' );
const request = require( './request' );

const nextService = process.env.NEXT_SERVICE;
const selfName = process.env.SELF_NAME;

const requestIdGlobal = ( req, res, next ) => {
  process.env.REQUEST_ID = res.get( 'X-Request-Id' );
  next();
};

app.use( addRequestId );
app.use( requestIdGlobal );

app.get( '/', async ( req, res ) => {
  logger( { requestId: process.env.REQUEST_ID } );

  let reply = await request(nextService);

  let bodyResponse = Object.assign( {}, reply );
  bodyResponse[selfName] = 'OK';

  res.json( bodyResponse );
} );

app.listen(3000, () => console.log( 'Example app listening on port 3000!' ) );
