package modelCollection;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import ai.djl.Application;
import ai.djl.Device;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.DetectedObjects.DetectedObject;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.YoloV5Translator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.Pipeline;
import ai.djl.translate.Translator;
import javafx.concurrent.Task;
import vegetationIndices.Calculator;
import yolointerface.Detection;
import yolointerface.ImageContainer;
import yolointerface.ImageType;

public class YoloPytorchCIR extends Task<Object> {

	Predictor<Image, DetectedObjects> predictor;

	ImageContainer container = ImageContainer.instance();
	
	private String modelPath = "C:\\AI\\yolov5\\yolov5\\runs\\train\\exp14\\weights\\best.torchscript";
	
	Calculator calculator = new Calculator();

	public YoloPytorchCIR() {

	}

	@Override
	protected Object call() throws Exception {
		Pipeline pipeline = new Pipeline();
		pipeline.add(new Resize(640, 640));
		pipeline.add(new ToTensor());

		Translator<Image, DetectedObjects> translator = YoloV5Translator.builder().setPipeline(pipeline)
				.optSynsetArtifactName("synset.txt").optThreshold(0.5f).build();

		Criteria<Image, DetectedObjects> criteria = Criteria.builder().optApplication(Application.CV.OBJECT_DETECTION)
				.setTypes(Image.class, DetectedObjects.class)
				.optModelPath(Paths.get(modelPath))
				.optTranslator(translator).optDevice(Device.gpu()).optProgress(new ProgressBar()).build();

		ZooModel<Image, DetectedObjects> model = criteria.loadModel();

		predictor = model.newPredictor();

		int count = ImageContainer.getRgbImages().size();

		int index = 0;

		for (File file : ImageContainer.getCirImages()) {
			ArrayList<Detection> detections = new ArrayList<Detection>();
			Image img = ImageFactory.getInstance().fromFile(Paths.get(file.getAbsolutePath()));
			img.getWrappedImage();
			DetectedObjects detectedTrees = predictor.predict(img);
			for (int i = 0; i < detectedTrees.getNumberOfObjects(); i++) {
				DetectedObject detectionInDjlFormat = detectedTrees.item(i);
				Detection detection = new Detection(detectionInDjlFormat.getBoundingBox().getBounds(),
						(int) (detectionInDjlFormat.getProbability() * 100), ImageType.CIR);
				calculator.setImage(file);
				double[] vegIndizes = calculator.calcMeanNDVI(detection);
				detection.setMeanNDVI(vegIndizes[0]);
				detection.setMeanGNDVI(vegIndizes[1]);
				detections.add(detection);
			}
			ImageContainer.getCIRDetections().get(file).addAll(detections);
			index++;
			this.updateProgress(index, count);
			
		}
		return null;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}
