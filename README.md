## solscraper

# Java application that listened to Raydiums token creation pool for newly minted tokens

1) Install **maven** and **Java 11** JDK
2) Clone this repo
3) run `mvn clean package` in the root dir
4) Set ENV Variables:
 **DISCORD_RAW_URL**={YOUR_CHANNEL_WEBHOOK}
 **DISCORD_TOKEN_URL**={YOUR_CHANNEL_WEBHOOK}
 **HELIUS**=https://mainnet.helius-rpc.com/?api-key={HELIUS_API_KEY}
 **HELIUS_LEGACY**=https://api.helius.xyz/v0/token-metadata?api-key={HELIUS_API_KEY}
5) run **java -jar ./target/solsraper.jar**
