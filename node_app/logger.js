const amqplib = require( 'amqplib' );

const QUEUE = 'logs';

const logOnRabbitMQ = async logPayload => {
  try {
    const connection = await amqplib.connect('amqp://rabbitmq');
    const channel = await connection.createChannel();

    const logContent = typeof logPayload == 'object' ? JSON.stringify( logPayload ) : logPayload;
    channel.sendToQueue( QUEUE, new Buffer( logContent ) );
  } catch( error ) {
    console.error( 'Error when trying to log on RabbitMQ: ', error );
  }
};

const logger = ( content, metadata = {} ) => {
  const logPayload = {
    timestamp: new Date(),
    level: 'debug',
    content
  };

  console.debug( logPayload );
  logOnRabbitMQ( logPayload );
};

module.exports = logger;
