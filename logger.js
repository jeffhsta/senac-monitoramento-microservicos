const logger = ( content, metadata = {} ) => {
  const logPayload = {
    timestamp: new Date(),
    level: 'debug',
    content
  };

  console.debug( logPayload );
  // TODO: send the log to RabbitMQ
};

module.exports = logger;
