package com.devspace.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(onNavigateToHome: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.onboarding),
            contentDescription = "Onboarding Image"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = colorResource(R.color.white),
                fontSize = 45.sp,
                fontWeight = FontWeight.SemiBold,
                text = "Let's"
            )

            Text(
                color = colorResource(R.color.white),
                fontSize = 45.sp,
                fontWeight = FontWeight.SemiBold,
                text = "Start Cooking"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                text = "Find the best recipes for cooking"
            )

            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = onNavigateToHome,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.red)
                )
            ) {
                Text(
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Medium,
                    text = "Start Cooking"
                )
            }

        }
    }

}