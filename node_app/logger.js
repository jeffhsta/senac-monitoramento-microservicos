const amqplib = require( 'amqplib' );

const QUEUE = 'logs';

const logOnRabbitMQ = async logPayload => {
  const connection = await amqplib.connect('amqp://rabbitmq');
  const channel = await connection.createChannel();
  await channel.assertQueue( QUEUE );

  const logContent = typeof logPayload == 'object' ? JSON.stringify( logPayload ) : logPayload;
  channel.sendToQueue( QUEUE, new Buffer( logContent ) );
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
