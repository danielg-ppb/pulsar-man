export interface ConsumerConfig {
    topicName: string;
    subscriptionName: string;
    subscriptionType: string;
    schemaType: string;
    initialPosition: string;
    outerClassName: string;
    mainInnerClassName: string;
}
