# Written-Digit-Classifier

For this project, I trained a neural network in Matlab to recognize handwritten numerical digits. The data used to train the neural network came from UCI's publically available Machine Learning Repository, an online resource hosting datasets for public use. More specific information on the dataset can be found at the website linked below. I used Java to pre-process the image, and Matlab's neural network suite (nnstart and its variants) to train the neural network used to classify images.

The data used comes from: https://archive.ics.uci.edu/ml/datasets/Semeion+Handwritten+Digit. The UCI page also contains a more in-depth explanation of the semeion.data dataset used to train the network. My thanks to Tactile Srl for making the data publically available.

Below is an start-to-finish workflow example that begins with the user writing and photographing a number and ends with the neural network attempting to identify the number you wrote.

1. Begin by writing any number on a blank, unlined sheet of paper. If possible, use a thicker pen instead of a pencil or ballpoint pen. Using any digital camera (a smartphone is fine), take a picture of the handwritten digit. Try to zoom in as much as possible (although not so far that the camera can't focus on your handwriting) and try to keep the number in the center of the screen. See /src/data/raw/uncropped for examples.

2. Using any kind of basic editor, crop the picture so that the number you wrote fills as much of the frame as possible. See /src/data/raw/cropped for examples.

3. If you haven't already, transfer the image to your computer. If you like, you can save it in the /data/ folder for easy access.

4. Before we can feed the image's pixel representation to the neural network, we need to do some preprocessing. First convert the picture to black and white using the convertToBlackAndWhite() method found in ImageProcessor.java. This will create a new image with the same name as your original picture with the suffix "_bw" appended (i.e. "cropped_7.jpg" and "cropped_7_bw.jpg"). Next, we need to scale the black and white image to a new size of 16x16 pixels. Use the resize() method in the ImageResizer.java class with scaledWidth and scaledHeight values of 16. Note that you'll need to supply the path to the black/white image as the first argument and a new path for the 16x16 image as the second argument.

5. Now that the image has been processed, we can extract the feature vector that we'll use as input to our ANN in order to identify individual digits. This feature vector is a 256-element list of binary values, where 1 is black and 0 is white. Calling ImageProcessor.createANNInput() with your BufferedImage object and the number you wrote on the paper will give you back the feature vector for that image. Copy and paste this vector to a text file and store it somewhere you can easily access. 

6. Before we plug the vector into our neural network, it's a good idea to see how it actually looks. You can use the DigitVisualizer.java class in the /frontend/ package to have Java Swing draw the image back to your screen. Ideally, you should be able to identify the number on the screen. If what you see on your screen doesn't look anything like the digit you wrote down, try re-writing it with a thicker marker and ensuring that you crop it according to the suggestions above.

7. Assuming your feature vector produces a recognizable image, we can now try to identify it. Copy the Digit_Recognition_Workspace.mat folder to your local Matlab workspace directory on your machine, then open Matlab and import the workspace. Store the feature vector in Matlab as a vector. Now, to see what the ANN thinks the digit is, use the Sem_Network object with your vector as an argument. For example, here is the feature vector for the processed_0_2.png image included in the /data/ folder:

>> pixels = [0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 0.0 0.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 0.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 0.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 0.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 0.0 0.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 0.0 0.0 0.0 0.0];

>> [output] = Sem_Network(pixels)

output =

    0.9984    0.0000    0.0000    0.0000    0.0012    0.0000    0.0000    0.0000    0.0000    0.0003
    
The 1x10 output you get is a vector of probabilities corresponding to how confident the neural network is that the image you fed it corresponds to a given digit, where output[0] corresponds to zero, output[1] to one, etc. Here, for example, the neural network outputs a 99.84% chance that the image we fed it is the number "0", a 0.00% chance it's the number "1", and a 0.03% chance that it's the number "9". This is a good sign since the vector we fed it did in fact correspond to the written number "0" since we used the processed_0_2.png image as input. 

Please note that this system is not perfect -- there are definitely situations in which the neural network will fail to identify the digit you wrote, or will only assign it a small probability. This seems to primarily happen with similar looking numbers, i.e. 3 and 8, 1 and 7, etc. The neural network was trained initially using the semeion.data training set to around 6.5% error, which is good enough to see meaningful results but obviously not good enough for production quality machine learning. Feel free to play around with everything and please let me know if you improve on it!

