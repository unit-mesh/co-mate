```kotlin
caseflow("CoffeeDelivery", defaultRole = "User") {
    activity("OrderManagement") {
        task("BrowseMenu") {
            stories = listOf("View available coffee options")
        }
        task("AddToCart") {
            stories = listOf("Select coffee and add it to the cart")
        }
        task("RemoveFromCart") {
            stories = listOf("Remove coffee from the cart")
        }
        task("UpdateCart") {
            stories = listOf("Update the quantity of coffee in the cart")
        }
        task("ProceedToCheckout") {
            stories = listOf("Proceed to the checkout process")
        }
    }
    activity("AccountManagement") {
        task("UserRegistration") {
            stories = listOf("Register with email", "Register with phone")
        }
        task("UserLogin") {
            stories = listOf("Login to the website")
        }
        task("ResetPassword") {
            stories = listOf("Reset password")
        }
    }
    activity("PaymentProcessing") {
        task("SelectPaymentMethod") {
            stories = listOf("Choose a payment method")
        }
        task("EnterPaymentDetails") {
            stories = listOf("Enter payment details")
        }
        task("VerifyPayment") {
            stories = listOf("Verify the payment")
        }
    }
    activity("DeliveryManagement") {
        task("SelectDeliveryAddress") {
            stories = listOf("Choose delivery address")
        }
        task("AddDeliveryNote") {
            stories = listOf("Add special delivery instructions")
        }
        task("TrackOrder") {
            stories = listOf("Track the status of the order")
        }
        task("CancelOrder") {
            stories = listOf("Cancel the order")
        }
    }
}
```