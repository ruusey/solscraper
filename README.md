## solscraper

# Java application that listens to Raydiums token creation pool for newly minted tokens

1) Install **maven** and **Java 11** JDK
2) Clone this repo
3) run `mvn clean package` in the root dir
4) Set ENV Variables:<br>
 **DISCORD_RAW_URL**={YOUR_CHANNEL_WEBHOOK}</br>
 **DISCORD_TOKEN_URL**={YOUR_CHANNEL_WEBHOOK}</br>
 **HELIUS**=https://mainnet.helius-rpc.com/?api-key={HELIUS_API_KEY}</br>
 **HELIUS_LEGACY**=https://api.helius.xyz/v0/token-metadata?api-key={HELIUS_API_KEY}</br>
 **TELEGRAM_CHANNEL_ID**={INT64_TARGET_CHANNEL_ID}</br>
 **TELEGRAM_BOT_TOKEN**={TELEGRAM_BOT_TOKEN}</br>
5) run **java -jar ./target/solsraper.jar**
